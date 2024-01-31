import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { CardRoutingModule } from './card-routing.module';
import {NewCardComponent} from "./new-card/new-card.component";
import { CardsComponent } from './cards/cards.component';
import { CardDetailComponent } from './card-detail/card-detail.component';
import {NavbarComponent} from "../navbar/navbar.component";
import {NavbarModule} from "../navbar/navbar.module";

@NgModule({
  declarations: [ CardsComponent, NewCardComponent, CardDetailComponent ],
  imports: [ SharedModule, CardRoutingModule, NavbarModule ]
})
export class CardModule {}
