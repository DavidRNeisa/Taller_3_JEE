import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { CourseListComponent } from './features/courses/pages/course-list/course-list.component';
import { CourseDetailComponent } from './features/courses/pages/course-detail/course-detail.component';
import { LessonViewerComponent } from './features/courses/pages/lesson-viewer/lesson-viewer.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'courses', component: CourseListComponent },
  { path: 'courses/:courseId/lessons/:lessonId', component: LessonViewerComponent },
  { path: 'courses/:id', component: CourseDetailComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' }
];

