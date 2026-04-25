import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { CourseListComponent } from './features/courses/pages/course-list/course-list.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'courses', component: CourseListComponent },
  { path: '**', redirectTo: '' }
];

