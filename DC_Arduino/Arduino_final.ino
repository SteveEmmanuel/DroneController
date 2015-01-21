/*
ARDUINO CODE FOR DC CAR
DC TEAM JOEL,JITHIN,STEVE
*/
 
int enablePin1 = 11;
int enablePin2 = 3;

int in1Pin = 10;
int in2Pin = 9;

int in21Pin = 6;
int in22Pin = 5;

 
void setup()
{
  Serial.begin(9600); 
  pinMode(enablePin1, OUTPUT);
  pinMode(enablePin2, OUTPUT);
  
  pinMode(in1Pin, OUTPUT);
  pinMode(in2Pin, OUTPUT);
  
  pinMode(in21Pin, OUTPUT);
  pinMode(in22Pin, OUTPUT);
  
  
}
 
void loop()
{

  int speed = 255;

  if(Serial.available())  
  {  
   int val = Serial.read();
   analogWrite(enablePin2, speed);
   analogWrite(enablePin1, speed);  
   setMotor(speed,val);  
   
  }  
  

  
}
 
void setMotor(int speed,int val)
{
  
  Serial.println(val);

if(val<=100)
{

Serial.println("Speed set");
float s = ((float)val/100)*speed;
analogWrite(enablePin1, s);
Serial.println(s);
}
 
if(val==102)
{
  Serial.println("down");
  digitalWrite(in1Pin,HIGH);
  digitalWrite(in2Pin,LOW);
}
if(val==101)
{
  Serial.println("up");

  digitalWrite(in1Pin,LOW);
  digitalWrite(in2Pin,HIGH);
  
} 
if(val==103)
{
  Serial.println("left");
  digitalWrite(in22Pin,HIGH);
  digitalWrite(in21Pin,LOW);
}
if(val==104)
{
  Serial.println("right");
  digitalWrite(in21Pin,LOW);
  digitalWrite(in22Pin,HIGH);
  
} 
if(val==105)
{
  Serial.println("stop");
  digitalWrite(in1Pin,HIGH);
  digitalWrite(in2Pin,HIGH);
  
} 

}


