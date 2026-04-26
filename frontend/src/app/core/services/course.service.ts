import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { Course } from '../../models/course.model';
import { Lesson } from '../../models/lesson.model';
import { Assignment } from '../../models/assignment.model';
import { Grade } from '../../models/grade.model';

interface BackendCourseResponse {
  id: number;
  titulo: string;
  descripcion: string;
  totalClases: number;
}

interface BackendLessonResourceResponse {
  id: number;
  type: string;
  title: string;
  url: string;
  order: number;
}

interface BackendLessonResponse {
  id: number;
  courseId: number;
  title: string;
  description: string;
  order: number;
  progress: number;
  completed: boolean;
  resources: BackendLessonResourceResponse[];
  hasAssignments: boolean;
  assignmentsDue?: string;
}

interface BackendAssignmentResponse {
  id: number;
  lessonId: number;
  courseId: number;
  title: string;
  description: string;
  dueDate: string;
  submittedDate?: string;
  deliveryStatus: Assignment['deliveryStatus'];
  grade?: number;
  feedback?: string;
  fileUrl?: string;
}

interface BackendGradeResponse {
  assignmentId: number;
  courseId: number;
  lessonId: number;
  assignmentTitle: string;
  dueDate: string;
  submittedDate?: string;
  score?: number;
  feedback?: string;
  status: Grade['status'];
}

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private readonly apiUrl = environment.apiUrl;

  constructor(private readonly http: HttpClient) { }

  getCourses(): Observable<Course[]> {
    return this.http.get<BackendCourseResponse[]>(`${this.apiUrl}/cursos`).pipe(
      map((courses) => courses.map((course, index) => this.mapCourse(course, index)))
    );
  }

  getCourseById(id: number): Observable<Course> {
    return this.http.get<BackendCourseResponse>(`${this.apiUrl}/cursos/${id}`).pipe(
      map((course) => this.mapCourse(course))
    );
  }

  getLessonsByCourse(courseId: number): Observable<Lesson[]> {
    return this.http.get<BackendLessonResponse[]>(`${this.apiUrl}/cursos/${courseId}/clases`).pipe(
      map((lessons) => lessons.map((lesson) => ({
        id: lesson.id,
        courseId: lesson.courseId,
        title: lesson.title,
        description: lesson.description,
        order: lesson.order,
        progress: lesson.progress ?? 0,
        completed: lesson.completed ?? false,
        resources: (lesson.resources ?? []).map((resource) => ({
          id: resource.id,
          type: this.normalizeResourceType(resource.type),
          title: resource.title,
          url: resource.url,
          order: resource.order
        })),
        hasAssignments: lesson.hasAssignments ?? false,
        assignmentsDue: lesson.assignmentsDue
      })))
    );
  }

  getAssignmentsByCourse(courseId: number): Observable<Assignment[]> {
    return this.http.get<BackendAssignmentResponse[]>(`${this.apiUrl}/cursos/${courseId}/tareas?alumnoId=1`).pipe(
      map((assignments) => assignments.map((assignment) => ({
        id: assignment.id,
        lessonId: assignment.lessonId,
        courseId: assignment.courseId,
        title: assignment.title,
        description: assignment.description,
        dueDate: assignment.dueDate,
        submittedDate: assignment.submittedDate,
        deliveryStatus: assignment.deliveryStatus,
        grade: assignment.grade,
        feedback: assignment.feedback,
        fileUrl: assignment.fileUrl
      })))
    );
  }

  getAssignmentById(courseId: number, assignmentId: number): Observable<Assignment> {
    return this.getAssignmentsByCourse(courseId).pipe(
      map((assignments) => {
        const assignment = assignments.find((item) => item.id === assignmentId);
        if (!assignment) {
          throw new Error('Assignment not found');
        }
        return assignment;
      })
    );
  }

  getGradesByCourse(courseId: number): Observable<Grade[]> {
    return this.http.get<BackendGradeResponse[]>(`${this.apiUrl}/cursos/${courseId}/calificaciones?alumnoId=1`).pipe(
      map((grades) => grades.map((grade) => ({
        assignmentId: grade.assignmentId,
        courseId: grade.courseId,
        lessonId: grade.lessonId,
        assignmentTitle: grade.assignmentTitle,
        dueDate: grade.dueDate,
        submittedDate: grade.submittedDate,
        score: grade.score,
        feedback: grade.feedback,
        status: grade.status
      })))
    );
  }

  private mapCourse(course: BackendCourseResponse, index = 0): Course {
    const lessonsCount = course.totalClases ?? 0;

    return {
      id: course.id,
      title: course.titulo,
      description: course.descripcion,
      lessonsCount,
      progress: this.calculateProgress(lessonsCount, index),
      enrolled: true
    };
  }

  private calculateProgress(lessonsCount: number, index: number): number {
    if (lessonsCount <= 0) {
      return 0;
    }

    return Math.min(100, lessonsCount * 15 + index * 5);
  }

  private normalizeResourceType(type: string): Lesson['resources'][number]['type'] {
    const normalized = (type ?? 'DOCUMENTO').toUpperCase();

    if (
      normalized === 'VIDEO' ||
      normalized === 'PRESENTACION' ||
      normalized === 'DOCUMENTO' ||
      normalized === 'PDF' ||
      normalized === 'ENLACE' ||
      normalized === 'IMAGEN' ||
      normalized === 'HTML'
    ) {
      return normalized;
    }

    return 'DOCUMENTO';
  }
}
