import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'location-service',
        data: { pageTitle: 'LocationServices' },
        loadChildren: () => import('./location-service/location-service.module').then(m => m.LocationServiceModule),
      },
      {
        path: 'booking',
        data: { pageTitle: 'Bookings' },
        loadChildren: () => import('./booking/booking.module').then(m => m.BookingModule),
      },
      {
        path: 'plrs-commission',
        data: { pageTitle: 'PlrsCommissions' },
        loadChildren: () => import('./plrs-commission/plrs-commission.module').then(m => m.PlrsCommissionModule),
      },
      {
        path: 'time-slot',
        data: { pageTitle: 'TimeSlots' },
        loadChildren: () => import('./time-slot/time-slot.module').then(m => m.TimeSlotModule),
      },
      {
        path: 'profit',
        data: { pageTitle: 'Profits' },
        loadChildren: () => import('./profit/profit.module').then(m => m.ProfitModule),
      },
      {
        path: 'analytics-center',
        data: { pageTitle: 'AnalyticsCenters' },
        loadChildren: () => import('./analytics-center/analytics-center.module').then(m => m.AnalyticsCenterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
