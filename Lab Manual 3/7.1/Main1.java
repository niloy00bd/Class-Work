class shape{
    
}
class twod extends shape{
    
}
class circle extends twod{
    double r;
    void area (double r){
        System.out.println("cirle area " + 3.1416*r*r);
    }
}
class square extends twod{
    double l;
    void area (double l){
        System.out.println("square area " + l*l);
    }
}
class triangle extends twod{
    double a,b;
    void area (double a, double b){
        System.out.println("triangle area " + 0.5*a*b);
    }
}
class threed extends shape{
    
}
class sphare extends threed{
    double r;
    void area (double r){
        System.out.println("sphare area " + 4*3.1416*r*r);
    }
}
class cube extends threed{
    double l;
    void area (double l){
        System.out.println("cube area " + 3*l*l);
    }
}
class tetrahedron extends threed{
    double l;
    void area (double l){
        System.out.println("tetrahedron area " + Math.sqrt(3)*l*l);
    }
}


public class Main1
{
	public static void main(String[] args) {
		circle a = new circle();
		a.area(2);
		square b = new square();
		b.area(2);
		triangle c = new triangle();
		c.area(2,3);
		sphare d = new sphare();
		d.area(2);
		cube e = new cube();
		e.area(2);
		tetrahedron f = new tetrahedron();
		f.area(2);
	}
}