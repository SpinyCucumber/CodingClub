uniform float time;
uniform sampler2D textureUnit;
uniform vec3 fog;

varying vec4 vertex;
varying vec2 texCoord;
uniform vec2 point;

void main() {
	vec4 color = texture2D(textureUnit, texCoord);
	float l = 1 / distance(gl_FragCoord.xy, point) * 200.0;
	gl_FragColor = vec4((l > 1) ? l * color.rgb : mix(fog, color.rgb, l), color.a); 
}