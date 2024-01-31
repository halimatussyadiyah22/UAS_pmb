import {ChangeDetectionStrategy, Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Observable, catchError, map, of, startWith } from 'rxjs';
import { DataState } from 'src/app/enum/dataStates.enum';
import { RegisterState } from 'src/app/interface/appStates';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class ResetPasswordComponent {
  resetPasswordState$: Observable<RegisterState> = of({dataState: DataState.LOADED});
  readonly DataState = DataState;

  constructor(private userService: UserService) {
  }

  resetPassword(resetPasswordForm: NgForm): void {
    this.resetPasswordState$ = this.userService.requestPasswordReset$(resetPasswordForm.value.email)
        .pipe(
            map(response => {
              console.log(response);
              resetPasswordForm.reset();
              return {dataState: DataState.LOADED, registerSuccess: true, message: response.message};
            }),
            startWith({dataState: DataState.LOADING, registerSuccess: false}),
            catchError((error: string) => {
              return of({dataState: DataState.ERROR, registerSuccess: false, error})
            })
        );
  }
}
