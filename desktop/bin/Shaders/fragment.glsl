#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform float time;
uniform float scale;
uniform float speed;
uniform vec2 resolution;

void main() {
		vec2 newCoords = vec2(v_texCoords.x + (time/scale)*speed, v_texCoords.y + (time/scale)*speed);
        vec4 color = texture2D(u_texture, newCoords).rgba;
        float gray = (color.r + color.g + color.b) / 3.0;
        vec3 grayscale = vec3(gray);

        gl_FragColor = vec4(color);
}