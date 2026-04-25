import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { CourseListComponent } from './features/courses/pages/course-list/course-list.component';
import { CourseDetailComponent } from './features/courses/pages/course-detail/course-detail.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'courses', component: CourseListComponent },
  { path: 'courses/:id', component: CourseDetailComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' }
];

