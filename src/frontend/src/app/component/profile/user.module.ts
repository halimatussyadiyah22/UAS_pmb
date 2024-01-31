import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { UserComponent } from './user/user.component';
import { UserRoutingModule } from './user-routing.module';
import {NavbarModule} from "../navbar/navbar.module";
import { UsersComponent } from './users/users.component';

@NgModule({
  declarations: [ UserComponent, UsersComponent ],
  imports: [ SharedModule, UserRoutingModule, NavbarModule ]
})
export class UserModule {}
