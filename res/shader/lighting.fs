uniform float time;
uniform sampler2D textureUnit;
uniform vec2 resolution;
uniform vec4 color;
uniform vec2 center;

varying vec4 vertex;
varying vec2 texCoord;
uniform vec2 point;

void main() {
	vec4 color = texture2D(textureUnit, texCoord);
	color /= distance(gl_FragCoord.xy, point) / 100;
   	gl_FragColor = color;
}