import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { CourseListComponent } from './features/courses/pages/course-list/course-list.component';
import { CourseDetailComponent } from './features/courses/pages/course-detail/course-detail.component';
import { LessonViewerComponent } from './features/courses/pages/lesson-viewer/lesson-viewer.component';
import { AssignmentListComponent } from './features/courses/pages/assignment-list/assignment-list.component';
import { SubmissionFormComponent } from './features/assignments/pages/submission-form/submission-form.component';
import { GradesOverviewComponent } from './features/grades/pages/grades-overview/grades-overview.component';
import { RecommendationsComponent } from './features/recommendations/recommendations.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'courses', component: CourseListComponent },
  { path: 'courses/:courseId/lessons/:lessonId', component: LessonViewerComponent },
  { path: 'courses/:courseId/assignments', component: AssignmentListComponent },
  { path: 'courses/:courseId/assignments/:assignmentId/submit', component: SubmissionFormComponent },
  { path: 'courses/:courseId/grades', component: GradesOverviewComponent },
  { path: 'recommendations', component: RecommendationsComponent },
  { path: 'courses/:id', component: CourseDetailComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' }
];

