import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CourseService } from '../../../../core/services/course.service';
import { Course } from '../../../../models/course.model';
import { Lesson } from '../../../../models/lesson.model';

@Component({
  selector: 'app-course-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './course-detail.component.html',
  styleUrl: './course-detail.component.css'
})
export class CourseDetailComponent implements OnInit {
  course: Course | null = null;
  lessons: Lesson[] = [];
  loading = true;
  courseId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.courseId = +params['id'];
      this.loadCourseAndLessons();
    });
  }

  private loadCourseAndLessons(): void {
    this.loading = true;

    this.courseService.getCourseById(this.courseId).subscribe({
      next: (course) => {
        this.course = course;
      },
      error: (err) => console.error('Error cargando curso:', err)
    });

    this.courseService.getLessonsByCourse(this.courseId).subscribe({
      next: (lessons) => {
        this.lessons = lessons;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error cargando lecciones:', err);
        this.loading = false;
      }
    });
  }

  getContentIcon(type: string): string {
    const icons: { [key: string]: string } = {
      'VIDEO': '🎥',
      'PRESENTACION': '📊',
      'DOCUMENTO': '📄',
      'PDF': '📕',
      'ENLACE': '🔗',
      'IMAGEN': '🖼️',
      'HTML': '🌐'
    };
    return icons[type] || '📎';
  }

  getProgressClass(progress: number): string {
    if (progress === 0) return 'progress-empty';
    if (progress < 50) return 'progress-low';
    if (progress < 100) return 'progress-mid';
    return 'progress-complete';
  }

  hasAssignmentsOverdue(dueDate: string | undefined): boolean {
    if (!dueDate) return false;
    return new Date(dueDate) < new Date();
  }
}
