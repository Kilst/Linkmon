attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform float time;

// simple vertex shader

void main()
{
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}