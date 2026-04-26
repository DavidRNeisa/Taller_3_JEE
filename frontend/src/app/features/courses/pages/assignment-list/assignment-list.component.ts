import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CourseService } from '../../../../core/services/course.service';
import { Course } from '../../../../models/course.model';
import { Assignment } from '../../../../models/assignment.model';

@Component({
  selector: 'app-assignment-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './assignment-list.component.html',
  styleUrls: ['./assignment-list.component.css'],
})
export class AssignmentListComponent implements OnInit {
  course: Course | null = null;
  assignments: Assignment[] = [];
  loading: boolean = true;
  courseId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.courseId = +params['courseId'];
      this.loadAssignments();
    });
  }

  loadAssignments(): void {
    this.loading = true;

    // Load course
    this.courseService.getCourseById(this.courseId).subscribe({
      next: (course: Course) => {
        this.course = course;

        // Load assignments
        this.courseService.getAssignmentsByCourse(this.courseId).subscribe({
          next: (assignments: Assignment[]) => {
            this.assignments = assignments.sort((a, b) => {
              return (
                new Date(a.dueDate).getTime() -
                new Date(b.dueDate).getTime()
              );
            });
            this.loading = false;
          },
          error: () => {
            this.assignments = [];
            this.loading = false;
          },
        });
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  getStatusLabel(status: string): string {
    const labels: { [key: string]: string } = {
      PENDING: 'Pendiente',
      SUBMITTED: 'Entregada',
      LATE: 'Entregada Tarde',
      NOT_SUBMITTED: 'No Entregada',
    };
    return labels[status] || status;
  }

  getStatusClass(status: string): string {
    return `status-${status.toLowerCase()}`;
  }

  getStatusIcon(status: string): string {
    const icons: { [key: string]: string } = {
      PENDING: '⏰',
      SUBMITTED: '✓',
      LATE: '⚠',
      NOT_SUBMITTED: '✗',
    };
    return icons[status] || '•';
  }

  isOverdue(dueDate: string): boolean {
    return new Date(dueDate) < new Date();
  }

  isDueSoon(dueDate: string): boolean {
    const now = new Date();
    const due = new Date(dueDate);
    const daysUntilDue = (due.getTime() - now.getTime()) / (1000 * 60 * 60 * 24);
    return daysUntilDue <= 3 && daysUntilDue > 0;
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  getDaysUntilDue(dueDate: string): number {
    const now = new Date();
    const due = new Date(dueDate);
    return Math.ceil((due.getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
  }

  filterByStatus(status: string): Assignment[] {
    return this.assignments.filter((a) => a.deliveryStatus === status);
  }

  get assignmentsSubmitted(): Assignment[] {
    return this.filterByStatus('SUBMITTED');
  }

  get assignmentsLate(): Assignment[] {
    return this.filterByStatus('LATE');
  }

  get assignmentsPending(): Assignment[] {
    return this.filterByStatus('PENDING');
  }

  get assignmentsNotSubmitted(): Assignment[] {
    return this.filterByStatus('NOT_SUBMITTED');
  }
}
