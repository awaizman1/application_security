package resource_server.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private Collection<GrantedAuthority> convertResourceRolesToAuth(String resource, List<String> roles) {
        String prefix = resource == null ? "ROLE_" : String.format("ROLE_%s.", resource);
        return roles.stream().map(role -> new SimpleGrantedAuthority(String.format("%s%s", prefix, role))).collect(Collectors.toList());
    }

    private Collection<GrantedAuthority> getRealmAccessRoles(Map<String, Object> access) {
        return getAccessRoles(null, access);
    }

    private Collection<GrantedAuthority> getAccessRoles(String accessName, Map<String, Object> access) {
        List<String> roles = (List<String>) access.get("roles");
        return convertResourceRolesToAuth(accessName, roles);
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {

        Map<String, Object> realmAccess = source.getClaimAsMap("realm_access");
        Map<String, Object> resourceAccess = source.getClaimAsMap("resource_access");

        var authorities = getRealmAccessRoles(realmAccess);

        var resourceAccessAuthorities = resourceAccess.entrySet().stream().flatMap(entry -> getAccessRoles(
                entry.getKey(),
                (Map<String, Object>) entry.getValue()).stream()).toList();

        authorities.addAll(resourceAccessAuthorities);

        return authorities;
    }
}