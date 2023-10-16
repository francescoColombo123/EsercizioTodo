package it.corsospring.todoApp.services;

import it.corsospring.todoApp.models.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse login(LoginModel loginModel) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.getUsername(),
                        loginModel.getPassword()
                ));
        UserDetails userModel = this.userService.loadUserByUsername(loginModel.getUsername());
        String jwtToken = this.jwtService.generateToken(userModel);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse signup(SignUpModel signUpModel) {
        UserModel user = new UserModel();
        user.setUsername(signUpModel.getUsername());
        user.setName(signUpModel.getName());
        user.setCognome(signUpModel.getCognome());
        user.setEmail(signUpModel.getEmail());
        user.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
        user.setRole(Role.USER);

        UserModel userNew = this.userService.saveUser(user);
        String jwtToken = this.jwtService.generateToken(userNew);
        return new AuthenticationResponse(jwtToken);
    }
}
