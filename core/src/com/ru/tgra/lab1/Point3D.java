package com.ru.tgra.lab1;

public class Point3D {

	public float x;
	public float y;
	public float z;

	public Point3D()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Point3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vector3D v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float distance(Point3D other) {
		return (float)Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
	}
	
	public Vector3D difference(Point3D other) {
		Vector3D tmp = new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
		tmp.normalize();
		return tmp;
	}
}
