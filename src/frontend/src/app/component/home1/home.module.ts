import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home/home.component';
import {NavbarComponent} from "../navbar/navbar.component";
import { StatsModule } from '../stats/stats.module';
import {NavbarModule} from "../navbar/navbar.module";

@NgModule({
  declarations: [ HomeComponent ],
    imports: [SharedModule, HomeRoutingModule, NavbarComponent, StatsModule, NavbarModule]
})
export class HomeModule {}
