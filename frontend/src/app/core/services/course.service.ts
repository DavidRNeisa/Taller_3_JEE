import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Course } from '../../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  constructor() { }

  getCourses(): Observable<Course[]> {
    // Mock data - será reemplazado por HTTP call a backend
    const mockCourses: Course[] = [
      { id: 1, title: 'Introducción a Listas', description: 'Aprende los conceptos básicos de listas enlazadas', lessonsCount: 34, progress: 0, enrolled: true },
      { id: 2, title: 'Pilas y Colas', description: 'Estructuras LIFO y FIFO', lessonsCount: 28, progress: 45, enrolled: true },
      { id: 3, title: 'Árboles Binarios', description: 'Estructuras jerárquicas fundamentales', lessonsCount: 36, progress: 0, enrolled: true },
      { id: 4, title: 'Grafos', description: 'Teoría de grafos y algoritmos', lessonsCount: 32, progress: 20, enrolled: false },
      { id: 5, title: 'Búsqueda y Ordenamiento', description: 'Algoritmos clásicos', lessonsCount: 26, progress: 0, enrolled: true },
      { id: 6, title: 'Hashing', description: 'Tablas hash y búsqueda rápida', lessonsCount: 18, progress: 0, enrolled: true },
    ];
    return of(mockCourses);
  }
}
