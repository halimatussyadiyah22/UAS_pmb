import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from 'src/app/guard/authentication.guard';
import { UserComponent } from './user/user.component';
import {UsersComponent} from "./users/users.component";

const userRoutes: Routes = [
  { path: '', children: [{ path: '', component: UserComponent, canActivate: [AuthenticationGuard] } ]},
  { path: 'users', component: UsersComponent, canActivate: [AuthenticationGuard] },

];

@NgModule({
  imports: [RouterModule.forChild(userRoutes)],
  exports: [RouterModule]
})
export class UserRoutingModule {}
