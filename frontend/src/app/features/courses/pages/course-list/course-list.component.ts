import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CourseService } from '../../../../core/services/course.service';
import { Course } from '../../../../models/course.model';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.css'
})
export class CourseListComponent implements OnInit {
  courses: Course[] = [];
  loading = true;

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.courseService.getCourses().subscribe({
      next: (data) => {
        this.courses = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando cursos:', err);
        this.loading = false;
      }
    });
  }

  getProgressClass(progress: number): string {
    if (progress === 0) return 'progress-empty';
    if (progress < 50) return 'progress-low';
    if (progress < 100) return 'progress-mid';
    return 'progress-complete';
  }

  viewCourse(courseId: number): void {
    // Navigation handled by routerLink
  }
}
