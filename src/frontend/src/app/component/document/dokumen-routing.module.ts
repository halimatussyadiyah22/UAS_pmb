import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from 'src/app/guard/authentication.guard';
import { DokumenComponent } from './dokumen/dokumen.component';
import { NewDokumenComponent } from './new-dokumen/new-dokumen.component';
import { DokumenDetailComponent } from './dokumen-detail/dokumen-detail.component';

const invoiceRoutes: Routes = [
  { path: 'cards', component: DokumenComponent, canActivate: [AuthenticationGuard] },
  { path: 'cards/new', component: NewDokumenComponent, canActivate: [AuthenticationGuard] },
  { path: 'cards/:id/:username', component: DokumenDetailComponent, canActivate: [AuthenticationGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(invoiceRoutes)],
  exports: [RouterModule]
})
export class DokumenRoutingModule { }
