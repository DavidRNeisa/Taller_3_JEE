import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CourseService } from '../../../../core/services/course.service';
import { Course } from '../../../../models/course.model';
import { Lesson } from '../../../../models/lesson.model';
import { ResourceViewerComponent } from '../../../../shared/components/resource-viewer/resource-viewer.component';

@Component({
  selector: 'app-lesson-viewer',
  standalone: true,
  imports: [CommonModule, RouterLink, ResourceViewerComponent],
  templateUrl: './lesson-viewer.component.html',
  styleUrls: ['./lesson-viewer.component.css'],
})
export class LessonViewerComponent implements OnInit {
  course: Course | null = null;
  lesson: Lesson | null = null;
  lessons: Lesson[] = [];
  currentLessonIndex: number = 0;
  loading: boolean = true;
  error: string | null = null;
  courseId: number = 0;
  lessonId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.courseId = +params['courseId'];
      this.lessonId = +params['lessonId'];
      this.loadLessonContent();
    });
  }

  loadLessonContent(): void {
    this.loading = true;
    this.error = null;

    // Get course
    this.courseService.getCourseById(this.courseId).subscribe({
      next: (course: Course) => {
        this.course = course;

        // Get lessons
        this.courseService.getLessonsByCourse(this.courseId).subscribe({
          next: (lessons: Lesson[]) => {
            this.lessons = lessons;
            this.findLesson();
            this.loading = false;
          },
          error: (err: any) => {
            this.error = 'Error al cargar las lecciones';
            this.loading = false;
          },
        });
      },
      error: (err: any) => {
        this.error = 'Error al cargar el curso';
        this.loading = false;
      },
    });
  }

  findLesson(): void {
    const index = this.lessons.findIndex((l) => l.id === this.lessonId);
    if (index !== -1) {
      this.currentLessonIndex = index;
      this.lesson = this.lessons[index];
    } else {
      this.error = 'Lección no encontrada';
    }
  }

  goToPreviousLesson(): void {
    if (this.currentLessonIndex > 0) {
      const prevLesson = this.lessons[this.currentLessonIndex - 1];
      this.router.navigate([
        '/courses',
        this.courseId,
        'lessons',
        prevLesson.id,
      ]);
    }
  }

  goToNextLesson(): void {
    if (this.currentLessonIndex < this.lessons.length - 1) {
      const nextLesson = this.lessons[this.currentLessonIndex + 1];
      this.router.navigate([
        '/courses',
        this.courseId,
        'lessons',
        nextLesson.id,
      ]);
    }
  }

  get hasPreviousLesson(): boolean {
    return this.currentLessonIndex > 0;
  }

  get hasNextLesson(): boolean {
    return this.currentLessonIndex < this.lessons.length - 1;
  }

  get progressPercentage(): number {
    if (this.lessons.length === 0) return 0;
    return Math.round(((this.currentLessonIndex + 1) / this.lessons.length) * 100);
  }

  getContentIcon(type: string): string {
    const icons: { [key: string]: string } = {
      VIDEO: '🎥',
      PRESENTACION: '📊',
      DOCUMENTO: '📄',
      PDF: '📑',
      ENLACE: '🔗',
      IMAGEN: '🖼️',
      HTML: '📱',
    };
    return icons[type] || '📎';
  }
}
