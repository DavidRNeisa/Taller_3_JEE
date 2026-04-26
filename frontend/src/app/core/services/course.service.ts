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
    // Mock data - 34 lecciones variadas
    const mockLessons: Lesson[] = [
      { id: 1, courseId, title: 'Conceptos Básicos de Listas', description: 'Introducción a estructuras de listas', order: 1, progress: 100, completed: true, resources: [
        { id: 1, type: 'VIDEO', title: 'Introducción a Listas', url: '/videos/intro-lists.mp4', order: 1 },
        { id: 2, type: 'DOCUMENTO', title: 'Apuntes en PDF', url: '/docs/lista-basico.pdf', order: 2 }
      ], hasAssignments: true, assignmentsDue: '2024-05-10' },
      { id: 2, courseId, title: 'Nodos y Enlaces', description: 'Estructura interna de una lista', order: 2, progress: 75, completed: false, resources: [
        { id: 3, type: 'PRESENTACION', title: 'Diapositivas', url: '/presentations/nodos.pptx', order: 1 },
        { id: 4, type: 'VIDEO', title: 'Explicación de enlaces', url: '/videos/enlaces.mp4', order: 2 }
      ], hasAssignments: true, assignmentsDue: '2024-05-12' },
      { id: 3, courseId, title: 'Inserción en Listas', description: 'Cómo agregar elementos', order: 3, progress: 50, completed: false, resources: [
        { id: 5, type: 'VIDEO', title: 'Inserción paso a paso', url: '/videos/insert.mp4', order: 1 },
        { id: 6, type: 'DOCUMENTO', title: 'Código de ejemplo', url: '/docs/insert-code.pdf', order: 2 }
      ], hasAssignments: true, assignmentsDue: '2024-05-15' },
      { id: 4, courseId, title: 'Eliminación de Elementos', description: 'Remover nodos de la lista', order: 4, progress: 0, completed: false, resources: [
        { id: 7, type: 'VIDEO', title: 'Eliminar nodos', url: '/videos/delete.mp4', order: 1 },
        { id: 8, type: 'IMAGEN', title: 'Diagrama de eliminación', url: '/images/delete-diagram.png', order: 2 }
      ], hasAssignments: true, assignmentsDue: '2024-05-17' },
      { id: 5, courseId, title: 'Búsqueda en Listas', description: 'Encontrar elementos', order: 5, progress: 0, completed: false, resources: [
        { id: 9, type: 'VIDEO', title: 'Algoritmo de búsqueda', url: '/videos/search.mp4', order: 1 }
      ], hasAssignments: true, assignmentsDue: '2024-05-19' },
    ];

    // Agregar más lecciones para llegar a 34
    for (let i = 6; i <= 34; i++) {
      mockLessons.push({
        id: i,
        courseId,
        title: `Lección ${i}: Tema avanzado ${i - 5}`,
        description: `Contenido sobre tema ${i}`,
        order: i,
        progress: Math.random() > 0.6 ? Math.floor(Math.random() * 100) : 0,
        completed: i <= 3,
        resources: [
          { id: 100 + i, type: 'VIDEO', title: `Video lección ${i}`, url: `/videos/lesson-${i}.mp4`, order: 1 },
          { id: 200 + i, type: 'DOCUMENTO', title: `Material lección ${i}`, url: `/docs/lesson-${i}.pdf`, order: 2 }
        ],
        hasAssignments: i % 2 === 0,
        assignmentsDue: new Date(Date.now() + i * 86400000).toISOString()
      });
    }

    return of(mockLessons);
  }

  getAssignmentsByCourse(courseId: number): Observable<Assignment[]> {
    // Mock data - tareas con diferentes estados
    const mockAssignments: Assignment[] = [
      {
        id: 1,
        lessonId: 1,
        courseId,
        title: 'Implementar una Lista Simple',
        description: 'Crea una clase que represente una lista enlazada con métodos básicos de inserción y eliminación',
        dueDate: new Date(Date.now() - 2 * 86400000).toISOString(), // 2 días atrás
        submittedDate: new Date(Date.now() - 1 * 86400000).toISOString(),
        deliveryStatus: 'LATE',
        grade: 85,
        feedback: 'Buen trabajo, pero faltó documentación en el código',
        fileUrl: '/submissions/lista-simple.zip'
      },
      {
        id: 2,
        lessonId: 2,
        courseId,
        title: 'Dibujar Nodos y Enlaces',
        description: 'Realiza un diagrama que muestre cómo funcionan los nodos y sus enlaces en una lista',
        dueDate: new Date(Date.now() - 5 * 86400000).toISOString(),
        submittedDate: new Date(Date.now() - 5.5 * 86400000).toISOString(),
        deliveryStatus: 'SUBMITTED',
        grade: 92,
        feedback: 'Excelente diagrama, muy claro y detallado',
        fileUrl: '/submissions/diagrama-nodos.pdf'
      },
      {
        id: 3,
        lessonId: 3,
        courseId,
        title: 'Algoritmo de Inserción',
        description: 'Escribe el pseudocódigo para insertar un elemento en una posición específica',
        dueDate: new Date(Date.now() + 2 * 86400000).toISOString(), // En 2 días
        deliveryStatus: 'PENDING'
      },
      {
        id: 4,
        lessonId: 4,
        courseId,
        title: 'Práctica de Eliminación',
        description: 'Implementa la función de eliminación y pruébala con múltiples casos',
        dueDate: new Date(Date.now() + 5 * 86400000).toISOString(),
        deliveryStatus: 'PENDING'
      },
      {
        id: 5,
        lessonId: 5,
        courseId,
        title: 'Búsqueda Binaria',
        description: 'Implementa búsqueda binaria en una lista ordenada',
        dueDate: new Date(Date.now() - 10 * 86400000).toISOString(),
        deliveryStatus: 'NOT_SUBMITTED'
      },
      {
        id: 6,
        lessonId: 6,
        courseId,
        title: 'Análisis de Complejidad',
        description: 'Analiza la complejidad temporal de las operaciones básicas',
        dueDate: new Date(Date.now() + 1 * 86400000).toISOString(), // Mañana
        deliveryStatus: 'PENDING'
      },
      {
        id: 7,
        lessonId: 7,
        courseId,
        title: 'Proyecto Integrador',
        description: 'Crea una aplicación que use listas para resolver un problema real',
        dueDate: new Date(Date.now() + 10 * 86400000).toISOString(),
        deliveryStatus: 'PENDING'
      },
    ];

    return of(mockAssignments);
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
    return this.getAssignmentsByCourse(courseId).pipe(
      map((assignments) => assignments.map((assignment) => {
        let status: Grade['status'] = 'NOT_DELIVERED';

        if (assignment.grade !== undefined) {
          status = 'GRADED';
        } else if (assignment.deliveryStatus === 'SUBMITTED' || assignment.deliveryStatus === 'LATE') {
          status = 'PENDING_REVIEW';
        }

        return {
          assignmentId: assignment.id,
          courseId,
          lessonId: assignment.lessonId,
          assignmentTitle: assignment.title,
          dueDate: assignment.dueDate,
          submittedDate: assignment.submittedDate,
          score: assignment.grade,
          feedback: assignment.feedback,
          status
        };
      }))
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
}
