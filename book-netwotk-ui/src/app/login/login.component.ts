import { Component } from '@angular/core';
import { AuthenticationRequest } from '../services/models';
import { AuthenticationService } from '../services/services/authentication.service';
import { Router } from '@angular/router';
import { TokenService } from '../services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: AuthenticationRequest = { email: '', password: '' };

  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) { }

  login() {
    this.errorMsg = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (response) => {
        this.tokenService.token = response.token as string;
        this.router.navigate(['/books']);
      },
      error: (error) => {
        console.log(error);
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        } else {
          this.errorMsg.push(error.error.error);
        }
      }
    });
  }

  registor() {
    this.router.navigate(['/register']);
  }

}
