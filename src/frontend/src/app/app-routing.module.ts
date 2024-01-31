import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./component/auth/login/login.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {ResetPasswordComponent} from "./component/auth/reset-password/reset-password.component";
import {VerifyComponent} from "./component/auth/verify/verify.component";
import {BiodataDetailComponent} from "./component/biodata/biodata-detail/biodata-detail.component";
import {UserComponent} from "./component/profile/user/user.component";
import {HomeComponent} from "./component/home1/home/home.component";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {NewBiodataComponent} from "./component/biodata/new-biodata/new-biodata.component";
import {NewCardComponent} from "./component/card/new-card/new-card.component";
import {CardsComponent} from "./component/card/cards/cards.component";

const routes: Routes = [
  {path: 'profile', loadChildren: () => import('./component/profile/user.module').then(module => module.UserModule)},
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: '**', component: HomeComponent, canActivate: [AuthenticationGuard]},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
