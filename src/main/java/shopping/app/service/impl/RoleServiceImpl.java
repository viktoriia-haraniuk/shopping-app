package shopping.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.app.model.Role;
import shopping.app.repository.RoleRepository;
import shopping.app.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role add(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(Role.RoleName roleName) {
        return roleRepository.getRoleByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Can't find role with name " + roleName));
    }
}
