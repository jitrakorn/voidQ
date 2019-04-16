import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomePageModule'
  },
  { path: 'login', loadChildren: './login/login.module#LoginPageModule' },
  { path: 'tabs', loadChildren: './pages/tabs/tabs.module#TabsPageModule' },
  { path: 'paymentpage', loadChildren: './paymentpage/paymentpage.module#PaymentpagePageModule' },
  { path: 'clinic-details', loadChildren: './clinic-details/clinic-details.module#ClinicDetailsPageModule' },
  { path: 'view-booking', loadChildren: './view-booking/view-booking.module#ViewBookingPageModule' }
 
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
