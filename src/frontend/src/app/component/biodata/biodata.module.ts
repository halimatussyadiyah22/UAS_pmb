import { NgModule } from '@angular/core';
import { BiodataRoutingModule } from './biodata-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import {BiodatasComponent} from "./biodatas/biodatas.component";
import {NewBiodataComponent} from "./new-biodata/new-biodata.component";
import {BiodataDetailComponent} from "./biodata-detail/biodata-detail.component";
import {NavbarComponent} from "../navbar/navbar.component";
import {NavbarModule} from "../navbar/navbar.module";

@NgModule({
  declarations: [
    BiodatasComponent,
    NewBiodataComponent,
    BiodataDetailComponent
  ],
  imports: [
    SharedModule,
    BiodataRoutingModule,
    NavbarComponent,
    NavbarModule
  ]
})
export class BiodataModule {}
