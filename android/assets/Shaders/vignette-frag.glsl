uniform sampler2D u_texture;

uniform vec2 resolution;

//RADIUS of our vignette, where 0.5 results in a circle fitting the screen
const float RADIUS = 0.75;

//softness of our vignette, between 0.0 and 1.0
const float SOFTNESS = 0.45;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords)*v_color;

    vec2 position = (gl_FragCoord.xy / resolution.xy) - vec2(0.5);

    float len = length(position);
    
    //use smoothstep to create a smooth vignette
	float vignette = smoothstep(0.7, 0.45, len);
	
	//apply the vignette with 50% opacity
	texColor.rgb = mix(texColor.rgb, texColor.rgb * vignette, 0.8);

    gl_FragColor = vec4( texColor.rgb * (1.0 - len), 1.0 );
}