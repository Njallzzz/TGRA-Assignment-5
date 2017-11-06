
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform int lightCount;
uniform vec3 u_lightPosition[4];
uniform vec3 u_cameraPosition;

varying vec2 v_uv;
varying vec3 v_n;
varying vec3 v_s[4];
varying vec3 v_h[4];

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal, 0.0);
	normal = u_modelMatrix * normal;
    
    // Global coordinates here
    v_uv = a_uv;
    v_n = normal.xyz;
    for(int i = 0; i < lightCount; i++) {
        // Diffuse Lighting
        vec3 s_tmp = u_lightPosition[i] - position.xyz;
        v_s[i] = s_tmp;
    
        // Specular lighting
        vec3 v = u_cameraPosition - position.xyz; 
        v_h[i] = v + s_tmp;
    }

	position = u_viewMatrix * position;
	normal = u_viewMatrix * normal;
    // Eye coordinates here
    
	gl_Position = u_projectionMatrix * position;
}