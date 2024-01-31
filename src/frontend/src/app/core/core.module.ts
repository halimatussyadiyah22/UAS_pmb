import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CacheInterceptor} from '../interceptor/cache.interceptor';
import {TokenInterceptor} from '../interceptor/token.interceptor';
import {UserService} from '../service/user.service';
import {HttpCacheService} from '../service/http.cache.service';
import {NotificationService} from "../service/notification.service";
import { BiodataService } from '../service/biodata.service';

@NgModule({
  imports: [
    HttpClientModule
  ],
  providers: [
    UserService, BiodataService, HttpCacheService, NotificationService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: CacheInterceptor, multi: true}
  ]
})
export class CoreModule {
}
