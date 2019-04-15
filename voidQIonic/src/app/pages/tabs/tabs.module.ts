import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      { path: 'healthbook', loadChildren: '../healthbook/healthbook.module#HealthbookPageModule' },
      { path: 'appointments', loadChildren: '../appointments/appointments.module#AppointmentsPageModule' }
    ]
  },
  {
    path: '',
    redirectTo:'tabs/healthbook',
    pathMatch : 'full'
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [TabsPage]
})
export class TabsPageModule {}
