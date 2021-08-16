package by.valvik.bannermanagement.security.service;

import by.valvik.bannermanagement.message.MessageProvider;
import by.valvik.bannermanagement.security.domain.Admin;
import by.valvik.bannermanagement.security.repository.AdminRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static by.valvik.bannermanagement.security.constant.SecurityConstant.ROLE_ADMIN;
import static java.util.Collections.unmodifiableList;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service("adminDetailsService")
public class AdminDetailsService implements UserDetailsService {

    private static final String LOGIN_INCORRECT = "login.incorrect";

    private final AdminRepository adminRepository;

    private final MessageProvider messageProvider;

    public AdminDetailsService(AdminRepository adminRepository, MessageProvider messageProvider) {

        this.adminRepository = adminRepository;

        this.messageProvider = messageProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByLogin(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(messageProvider.getMessage(LOGIN_INCORRECT,
                                                                                                                 username)));

        return new AdminDetails(admin);

    }

    static final class AdminDetails extends Admin implements UserDetails {

        private static final List<GrantedAuthority> AUTHORITIES = unmodifiableList(createAuthorityList(ROLE_ADMIN));

        AdminDetails(Admin admin) {

            super(admin.getId(), admin.getLogin(), admin.getPassword());

        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            return AUTHORITIES;

        }

        @Override
        public String getUsername() {

            return getLogin();

        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {

            return true;

        }

        @Override
        public boolean isCredentialsNonExpired() {

            return true;

        }

        @Override
        public boolean isEnabled() {

            return true;

        }

    }

}
