import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from 'src/app/guard/authentication.guard';
import {BiodatasComponent} from "./biodatas/biodatas.component";
import {NewBiodataComponent} from "./new-biodata/new-biodata.component";
import {BiodataDetailComponent} from "./biodata-detail/biodata-detail.component";

const customerRoutes: Routes = [
  { path: 'biodatas', component: BiodatasComponent, canActivate: [AuthenticationGuard] },
  { path: 'biodatas/new', component: NewBiodataComponent, canActivate: [AuthenticationGuard] },
  { path: 'biodatas/:id', component: BiodataDetailComponent, canActivate: [AuthenticationGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(customerRoutes)],
  exports: [RouterModule]
})
export class BiodataRoutingModule {}
