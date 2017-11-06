package com.ru.tgra.lab1;

public class BezierCurve {
	private Point3D p1, p2, p3, p4;
	
	public BezierCurve(Point3D origin_, Vector3D start_, Vector3D end_, Point3D destination_) {
		this.p1 = origin_;
		this.p4 = destination_;
		this.p2 = new Point3D(p1.x + start_.x, p1.y + start_.y, p1.z + start_.z);
		this.p3 = new Point3D(p4.x - end_.x, p4.y - end_.y, p4.z - end_.z);
	}
	
	public Point3D calculate(float t) {
		Point3D p = new Point3D();
		
		p.x = (float) ((float) (Math.pow((1.0f-t), 3) * p1.x)
				+ (3 * Math.pow((1.0f-t), 2) * t * p2.x)
				+ (3 * Math.pow(t, 2) * (1.0f-t) * p3.x)
				+ (Math.pow(t, 3) * p4.x));
		
		p.y = (float) ((float) (Math.pow((1.0f-t), 3) * p1.y)
				+ (3 * Math.pow((1.0f-t), 2) * t * p2.y)
				+ (3 * Math.pow(t, 2) * (1.0f-t) * p3.y)
				+ (Math.pow(t, 3) * p4.y));
		
		p.z = (float) ((float) (Math.pow((1.0f-t), 3) * p1.z)
				+ (3 * Math.pow((1.0f-t), 2) * t * p2.z)
				+ (3 * Math.pow(t, 2) * (1.0f-t) * p3.z)
				+ (Math.pow(t, 3) * p4.z));
		
		return p;
	}
}
