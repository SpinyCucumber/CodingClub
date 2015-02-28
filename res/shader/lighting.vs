varying vec4 vertex;
varying vec2 texCoord;

void main() {
    vertex = gl_Vertex;
    gl_Position = gl_ModelViewProjectionMatrix * vertex;
    texCoord = gl_MultiTexCoord0.xy;
}