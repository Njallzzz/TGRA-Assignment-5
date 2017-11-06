
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_diffuseTexture;
uniform float u_usesDiffuseTexture;

uniform int lightCount;
uniform vec4 u_lightIntensity[4];
uniform float u_constantAttenuation[4];
uniform float u_linearAttenuation[4];
uniform float u_quadraticAttenuation[4];

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform float u_materialShinyness;
uniform vec4 u_materialAmbient;
uniform vec4 u_materialEmission;

varying vec2 v_uv;
varying vec3 v_n;
varying vec3 v_s[4];
varying vec3 v_h[4];

void main()
{
    vec3 normal = normalize(v_n);
   
    vec4 materialDiffuse;
    if(u_usesDiffuseTexture == 1.0) {
        materialDiffuse = texture2D(u_diffuseTexture, v_uv);
    } else {
        materialDiffuse = u_materialDiffuse;
    }

    vec4 color = vec4(0.0, 0.0, 0.0, 1.0);
    for(int i = 0; i < lightCount; i++) {
        float s_length = length(v_s[i]);
        vec3 s_norm = normalize(v_s[i]);
        vec3 h_norm = normalize(v_h[i]);
    
        // Diffuse
        float lambert = max(0.0, dot(normal, s_norm));
        // Specular
        float phong = max(0.0, dot(normal, h_norm));
        
        // Diffuse
        vec4 addedcolor = lambert * u_lightIntensity[i] * materialDiffuse;
        // Specular
        addedcolor += pow(phong, u_materialShinyness) * u_lightIntensity[i] * u_materialSpecular;
        // Attenuation
        float attenuation = 1.0 / (u_constantAttenuation[i] + s_length * u_linearAttenuation[i] + pow(s_length, 2.0) * u_quadraticAttenuation[i]);
        addedcolor *= attenuation;
        
        // Diffuse and Specular
        color += addedcolor;
        // Ambient
        color += u_lightIntensity[i] * u_materialAmbient;
    }
    
    // Emissive
    color += u_materialEmission;
    
	gl_FragColor = vec4(color.rgb, 1.0);
}