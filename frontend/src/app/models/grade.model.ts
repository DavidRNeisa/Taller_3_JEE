export type GradeStatus = 'GRADED' | 'PENDING_REVIEW' | 'NOT_DELIVERED';

export interface Grade {
	assignmentId: number;
	courseId: number;
	lessonId: number;
	assignmentTitle: string;
	dueDate: string;
	submittedDate?: string;
	score?: number; // 0-100
	feedback?: string;
	status: GradeStatus;
}
