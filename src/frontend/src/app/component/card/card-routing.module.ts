import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from 'src/app/guard/authentication.guard';
import {CardsComponent} from "./cards/cards.component";
import {NewCardComponent} from "./new-card/new-card.component";
import {CardDetailComponent} from "./card-detail/card-detail.component";

const invoiceRoutes: Routes = [
  { path: 'cards', component: CardsComponent, canActivate: [AuthenticationGuard] },
  { path: 'cards/new', component: NewCardComponent, canActivate: [AuthenticationGuard] },
  { path: 'cards/:id/:username', component: CardDetailComponent, canActivate: [AuthenticationGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(invoiceRoutes)],
  exports: [RouterModule]
})
export class CardRoutingModule { }
