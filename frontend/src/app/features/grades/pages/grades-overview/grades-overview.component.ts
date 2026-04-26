import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CourseService } from '../../../../core/services/course.service';
import { Course } from '../../../../models/course.model';
import { Grade } from '../../../../models/grade.model';

@Component({
	selector: 'app-grades-overview',
	standalone: true,
	imports: [CommonModule, RouterLink],
	templateUrl: './grades-overview.component.html',
	styleUrl: './grades-overview.component.css'
})
export class GradesOverviewComponent implements OnInit {
	courseId = 0;
	course: Course | null = null;
	grades: Grade[] = [];
	loading = true;
	error = '';

	constructor(
		private route: ActivatedRoute,
		private courseService: CourseService
	) {}

	ngOnInit(): void {
		this.route.params.subscribe((params) => {
			this.courseId = +params['courseId'];
			this.loadGrades();
		});
	}

	private loadGrades(): void {
		this.loading = true;
		this.error = '';

		this.courseService.getCourseById(this.courseId).subscribe({
			next: (course) => {
				this.course = course;
			}
		});

		this.courseService.getGradesByCourse(this.courseId).subscribe({
			next: (grades) => {
				this.grades = [...grades].sort((a, b) => new Date(b.dueDate).getTime() - new Date(a.dueDate).getTime());
				this.loading = false;
			},
			error: () => {
				this.error = 'No se pudieron cargar las calificaciones.';
				this.loading = false;
			}
		});
	}

	get gradedItems(): Grade[] {
		return this.grades.filter((grade) => grade.status === 'GRADED');
	}

	get pendingReviewItems(): Grade[] {
		return this.grades.filter((grade) => grade.status === 'PENDING_REVIEW');
	}

	get averageScore(): number {
		if (this.gradedItems.length === 0) {
			return 0;
		}

		const total = this.gradedItems.reduce((sum, item) => sum + (item.score || 0), 0);
		return Math.round(total / this.gradedItems.length);
	}

	getStatusLabel(status: Grade['status']): string {
		const labels: Record<Grade['status'], string> = {
			GRADED: 'Calificada',
			PENDING_REVIEW: 'Pendiente de revision',
			NOT_DELIVERED: 'Sin entrega'
		};

		return labels[status];
	}

	getStatusClass(status: Grade['status']): string {
		return `status-${status.toLowerCase()}`;
	}

	formatDate(dateString?: string): string {
		if (!dateString) {
			return '-';
		}

		return new Date(dateString).toLocaleString('es-ES', {
			day: '2-digit',
			month: 'short',
			year: 'numeric',
			hour: '2-digit',
			minute: '2-digit'
		});
	}
}
