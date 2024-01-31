import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {ResetPasswordComponent} from "./component/reset-password/reset-password.component";
import {VerifyComponent} from "./component/verify/verify.component";
import {BiodataDetailComponent} from "./component/biodata-detail/biodata-detail.component";
import {UserComponent} from "./component/user/user.component";
import {HomeComponent} from "./component/home/home.component";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {NewBiodataComponent} from "./component/new-biodata/new-biodata.component";
import {NewCardComponent} from "./component/new-card/new-card.component";
import {CardsComponent} from "./component/cards/cards.component";
import {BiodatasComponent} from "./component/biodatas/biodatas.component";
import {CardDetailComponent} from "./component/card-detail/card-detail.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'resetpassword', component: ResetPasswordComponent},
  {path: 'user/verify/account/:key', component: VerifyComponent},
  {path: 'user/verify/password/:key', component: VerifyComponent},
  {path: 'profile', component: UserComponent, canActivate: [AuthenticationGuard]},
  {path: 'customers', component: BiodatasComponent, canActivate: [AuthenticationGuard]},
  {path: 'customers/new', component: NewBiodataComponent, canActivate: [AuthenticationGuard]},
  {path: 'invoices/new', component: NewCardComponent, canActivate: [AuthenticationGuard]},
  {path: 'invoices', component: CardsComponent, canActivate: [AuthenticationGuard]},
  {path: 'customers/:id', component: BiodataDetailComponent, canActivate: [AuthenticationGuard]},
  {path: 'invoices/:id/:invoiceNumber', component: CardDetailComponent, canActivate: [AuthenticationGuard]},
  {path: '', component: HomeComponent, canActivate: [AuthenticationGuard]},
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: '**', component: HomeComponent}];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
