import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { DokumenRoutingModule } from './dokumen-routing.module';
import {NavbarModule} from "../navbar/navbar.module";
import { CardsComponent } from '../card/cards/cards.component';
import { DokumenComponent } from './dokumen/dokumen.component';
import { NewDokumenComponent } from './new-dokumen/new-dokumen.component';
import { DokumenDetailComponent } from './dokumen-detail/dokumen-detail.component';

@NgModule({
  declarations: [ DokumenComponent, NewDokumenComponent, DokumenDetailComponent ],
  imports: [ SharedModule, DokumenRoutingModule, NavbarModule ]
})
export class DokumenModule {}
