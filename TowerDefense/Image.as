//Image class, allows easy rotation of image
package{
	import flash.display.Bitmap;
	import flash.geom.Matrix;
	
	public class Image
	{
		//variables
		private var location:String;
		private var x:int;
		private var y:int;
		private var pivotx:int;
		private var pivoty:int;
		private var img:Bitmap;
		private var width:int;
		private var height:int;
		private var matrix:Matrix;
		private var angle=0;
		private var xscale = 1;
		private var yscale = 1;
		
		//constructor
		public function Image(tmpimg:Bitmap,x:int,y:int,pivotx:int,pivoty:int)
		{
			this.location = location;
			this.pivotx = pivotx;
			this.pivoty = pivoty;
			this.x = x;
			this.y = y;
			img = tmpimg;
			img.smoothing=true;
			width = img.width;
			height = img.height;
			img.x = x;
			img.y = y;
			retransform;
		}
		
		public function getImage():Bitmap{
			return img;
		}
		
		//rotate adds angle
		public function rotate(degrees):void{
			this.angle +=degrees;
			retransform();
		}
		
		//sets angle
		public function setAngle(degrees):void{
			this.angle =degrees;
			retransform();
		}
		
		//gets x position of the pivot
		public function getX():int{
			return x;
		}
		
		//gets y position of the pivot
		public function getY():int{
			return y;
		}
		
		//gets true x of image
		public function getXComp():int{
			var r = Math.sqrt(pivotx*pivotx+pivoty*pivoty);
			var theta = Math.atan2(-pivoty,pivotx);
			return x+(int)(r*Math.cos(-angle+theta));
		}
		
		//get true y of image
		public function getYComp():int{
			var r = Math.sqrt(pivotx*pivotx+pivoty*pivoty);
			var theta = Math.atan2(-pivoty,pivotx);
			return y+(int)(r*Math.sin(-angle+theta));
		}
		
		//set x position of pivot
		public function setX(x:int):void{
			this.x = x;
			retransform();
		}
		
		//set y position of pivot
		public function setY(y:int):void{
			this.y =y;
			retransform();
		}
		
		//refresh transform
		private function retransform():void{
			matrix = new Matrix();
			matrix.rotate(angle);
			matrix.scale(xscale,yscale);
			img.transform.matrix = matrix;
			var r = Math.sqrt(pivotx*pivotx+pivoty*pivoty);
			var theta = Math.atan2(-pivoty,pivotx);
			img.x=x-(int)(r*Math.cos(-angle+theta));
			img.y=y+(int)(r*Math.sin(-angle+theta));
		}
		
		//change image
		public function changeImage(replacement:Bitmap):void{
			img.bitmapData = replacement.bitmapData;
			retransform();
			img.smoothing=true;
		}
		
		//increase alpha
		public function increaseAlpha(amount):void{
			if(amount+img.alpha>=1)
				img.alpha=1;
			else
				img.alpha+=amount;
		}
		
		//decrease alpha
		public function decreaseAlpha(amount):void{
			if(img.alpha-amount<=0)
				img.alpha=0;
			else
				img.alpha-=amount;
		}
		
		//get alpha
		public function getAlpha(){
			return img.alpha;
		}
		
		//get angle
		public function getAngle(){
			return angle;
		}
		
		public function setXScale(xs){
			xscale = xs;
		}
		
		public function setYScale(ys){
			yscale = ys;
		}
	}				
}