export type DeliveryStatus = 'PENDING' | 'SUBMITTED' | 'LATE' | 'NOT_SUBMITTED';

export interface Assignment {
  id: number;
  lessonId: number;
  courseId: number;
  title: string;
  description: string;
  dueDate: string; // ISO date
  submittedDate?: string; // ISO date - when student submitted
  deliveryStatus: DeliveryStatus;
  grade?: number; // 0-100
  feedback?: string;
  fileUrl?: string; // URL to submitted work
}
