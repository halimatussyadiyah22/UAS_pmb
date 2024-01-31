import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { VerifyComponent } from './component/verify/verify.component';
import {ResetPasswordComponent} from "./component/reset-password/reset-password.component";
import {BiodatasComponent} from "./component/biodatas/biodatas.component";
import {BiodataDetailComponent} from "./component/biodata-detail/biodata-detail.component";
import { HomeComponent } from './component/home/home.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import { StatsComponent } from './component/stats/stats.component';
import {NewCardComponent} from "./component/new-card/new-card.component";
import {CardsComponent} from "./component/cards/cards.component";
import {CardDetailComponent} from "./component/card-detail/card-detail.component";
import {UserComponent} from "./component/user/user.component";
import { ExtractArrayValue } from './pipes/extravalue.pipe';
import {UsersComponent} from "./component/users/users.component";

@NgModule({
  declarations: [
      AppComponent,
    AppComponent,
    LoginComponent,
    RegisterComponent,
    VerifyComponent,
    ResetPasswordComponent,
    BiodatasComponent,
    BiodataDetailComponent,
    HomeComponent,
    NavbarComponent,
    StatsComponent,
    NewCardComponent,
    CardsComponent,
    CardDetailComponent,
    UserComponent,
      UsersComponent,
      LoginComponent,
    ExtractArrayValue],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
