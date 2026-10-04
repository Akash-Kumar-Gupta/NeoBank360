import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';

import { LoginComponent } from './login.component';
import { AuthService } from '../auth.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    authServiceSpy = jasmine.createSpyObj('AuthService', [
      'login',
      'saveToken',
      'isAdmin'
    ]);

    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the login component', () => {
    expect(component).toBeTruthy();
  });

  it('should toggle password visibility', () => {
    expect(component.showPassword).toBeFalse();

    component.togglePasswordVisibility();
    expect(component.showPassword).toBeTrue();

    component.togglePasswordVisibility();
    expect(component.showPassword).toBeFalse();
  });

  it('should submit login form and navigate to dashboard', () => {
    authServiceSpy.login.and.returnValue(
      of({ token: 'fake-jwt-token' })
    );
    authServiceSpy.isAdmin.and.returnValue(false);

    component.form.setValue({
      email: 'test@example.com',
      password: 'password123'
    });

    component.submit();

    expect(authServiceSpy.login).toHaveBeenCalled();
    expect(authServiceSpy.saveToken).toHaveBeenCalledWith('fake-jwt-token');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/dashboard']);
  });
});