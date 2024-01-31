import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import {CoreModule} from "./core/core.module";
import { AuthModule } from './component/auth/auth.module';
import { CardModule } from './component/card/card.module';
import { HomeModule } from './component/home1/home.module';
import { NotificationModule } from './notification.module';
import { BiodataModule } from './component/biodata/biodata.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    CoreModule,
    AuthModule,
    BiodataModule,
    CardModule,
    HomeModule,
    AppRoutingModule,
    NotificationModule,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
