from flask import Blueprint, abort, request, render_template, redirect, flash

import requests
import json


router= Blueprint('router',__name__)

# @router.route('/')
# def home():
    
#     return render_template('index.html')

# python3 -m venv virtual source virtual/bin/activate pip install flask pip install requests

@router.route('/')
def admin():
    
    return render_template('fragmento/familia/lista.html')



@router.route('/admin/family/register')
def view_register_family():
    r= requests.get("http://localhost:8099/myapp/family/list")
    data = r.json()
    print(r.json())
    return render_template ('fragmento/familia/registro.html', lista= data ["data"])




#Editar familia
@router.route('/admin/family/edit/<id>')
def view_edit_family(id):
    #r = requests.get("http://localhost:8099/myapp/family/listType")
    #data = r.json()
    r = requests.get("http://localhost:8099/myapp/family/get/"+id)

    data = r.json()
    if(r.status_code == 200):
        return render_template('fragmento/familia/editar.html', family = data["data"])
    else:
        flash(data["data"], category='error')
        return redirect("/admin/family/list")
    
    
    
#########
@router.route('/admin/family/list')
def list_family():
    try:
        # Hacer la solicitud al servicio externo
        r = requests.get("http://localhost:8099/myapp/family/list")
        
        # Verificar si la respuesta fue exitosa
        if r.status_code == 200:
            data = r.json()
            if "data" in data and isinstance(data["data"], list):
                # Renderizar la lista de familias
                return render_template('fragmento/familia/lista.html', lista=data["data"])
            else:
                # Manejar respuesta sin datos válidos
                flash("No se encontraron datos de familias.", category="warning")
                return render_template('fragmento/familia/lista.html', lista=[])
        else:
            # Manejar error en la respuesta
            flash(f"Error al obtener datos: {r.status_code} - {r.text}", category="error")
            return render_template('fragmento/familia/lista.html', lista=[])
    except requests.RequestException as e:
        # Manejar errores de conexión u otros problemas de la solicitud
        flash(f"No se pudo conectar al servicio: {str(e)}", category="error")
        return render_template('fragmento/familia/lista.html', lista=[])



@router.route('/admin/family/save', methods=["POST"])
def save_family():
    headers = {'Content-type': 'application/json'}
    form = request.form

    try:
        # Construcción de los datos
        dataF = {
            "apellidos": form["ape"],
            "direccion": form["dir"],
            "sector": form["sec"],
            "codigoHogar": form["cod"],
            "adquirirGenerador": form["adq"].lower() == 'true',
            "generador": {
                "coste": form.get("coste"),
                "potencia": form.get("potencia"),
                "consumoHora": form.get("consumo"),
                "tipoUso": form.get("tipo_uso"),
            } if form["adq"].lower() == 'true' else None
        }

        # Enviar datos al servidor
        r = requests.post("http://localhost:8099/myapp/family/save", data=json.dumps(dataF), headers=headers)

        # Verificar la respuesta
        if r.status_code == 200:
            flash("Se ha guardado correctamente", category='info')
            return redirect("/admin/family/list")
        else:
            # Manejar errores del backend
            flash(f"Error al guardar: {r.text}", category='error')
            return redirect("/admin/family/register")
    except Exception as e:
        # Manejar excepciones generales
        flash(f"Error inesperado: {str(e)}", category='error')
        return redirect("/admin/family/register")


#Metodo de actualizar

# Método de actualizar
@router.route('/admin/family/update', methods=["POST"])
def update_family():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    try:
        # Construcción de los datos del formulario
        dataF = {
            "id": form.get("id"),
            "apellidos": form.get("ape"),
            "direccion": form.get("dir"),
            "sector": form.get("sec"),
            "codigoHogar": form.get("cod"),
            "adquirirGenerador": form.get("adq", "").lower() == 'true',
            "generador": {
                "id": form.get("id"),
                "coste": form.get("coste"),
                "potencia": form.get("potencia"),
                "consumoHora": form.get("consumo"),
                "tipoUso": form.get("tipo_uso"),
            } if form.get("adq", "").lower() == 'true' else None
        }

        # Verificar que los campos requeridos no sean nulos
        required_fields = ["id", "apellidos", "direccion", "sector", "codigoHogar", "adq"]
        for field in required_fields:
            if not dataF.get(field):
                flash(f"El campo {field} es obligatorio.", category='error')
                return redirect("/admin/family/edit/" + form.get("id", ""))

        # Realizar la solicitud al servidor backend
        r = requests.post("http://localhost:8099/myapp/family/update", 
                           data=json.dumps(dataF), headers=headers)

        # Manejar la respuesta del servidor
        if r.status_code == 200:
            flash("Se ha actualizado correctamente la familia.", category='success')
            return redirect("/admin/family/list")
        else:
            # Manejo de errores con más detalle
            try:
                error_message = r.json().get("message", "Error desconocido")
            except ValueError:
                error_message = r.text
            flash(f"Error al actualizar: {error_message}", category='error')
            return redirect("/admin/family/edit/" + form.get("id", ""))

    except Exception as e:
        # Manejo de excepciones generales
        flash(f"Error inesperado: {str(e)}", category='error')
        return redirect("/admin/family/edit/" + form.get("id", ""))
    
    
    
    
    

###########generador
@router.route('/admin/generador/save', methods=["POST"])
def save_generador():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "coste": form["cost"],
        "potencia": form["pot"],
        "consumoHora": form["cons"],
        "codigoHogar": form["cod"], 
        "tipoUso": form["tip"],
    }
    r = requests.post("http://localhost:8099/myapp/generador/save", data = json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code==200:
        flash("Se ha guardado correctamente", category='info')
        return redirect("/admin/generator/list")
    else:
        flash(str(dat["data"],category='error'))
        return redirect("/admin/generator/list")
    

@router.route('/admin/generator/register/<id>')
def view_register_generator(id):
    r = requests.get(f"http://localhost:8099/myapp/generator/get/{id}")
    data = r.json()
    print(data)
    return render_template('fragmento/generador/registro.html', lista=data["data"])




#lista de generadores
@router.route('/admin/generator/list/<id>')
def list_generator(id):
    try:
        # Solicitar datos a la API
        r = requests.get("http://localhost:8099/myapp/generator/list")
        r.raise_for_status()  # Verifica que la respuesta sea 2xx
        data = r.json()
        
        # Verifica que los datos tengan la estructura esperada
        if "data" not in data:
            raise ValueError("Respuesta de la API no contiene 'data'")
        
        # Renderiza la plantilla con los datos
        return render_template(
            'fragmento/generador/lista.html',
            lista=data["data"],
            id=id
        )
    except requests.exceptions.RequestException as e:
        print(f"Error al conectar con la API: {e}")
        abort(500, description="Error al obtener los datos de la API")
    except ValueError as e:
        print(f"Error en el formato de respuesta: {e}")
        abort(500, description="Datos de la API no válidos")



@router.route('/admin/generator/save', methods=["POST"])
def save_generator():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "coste": form["coste"],
        "potencia": form["potencia"],
        "consumoHora": form["consumo"],
        "tipo_usor": form["tipo_uso"], 
       
    }
    r = requests.post("http://localhost:8099/myapp/generator/save", data = json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code==200:
        flash("Se ha guardado correctamente", category='info')
        return redirect("/admin/generator/list")
    else:
        ##flash(str(dat["data"],category='error'))
        flash(str(dat["data"]), category='error')
        return redirect("/admin/generator/list")

# ORDENAR FAMILIAS
@router.route('/admin/family/order/<attribute>/<type>/<metodo>', methods=["GET"])
def ordenar(attribute, type, metodo):
    try:
        # Solicitud al servicio backend
        r = requests.get(f"http://localhost:8099/myapp/family/orderFamily/{attribute}/{type}/{metodo}")
        r.raise_for_status()
        data1 = r.json()
        if "data" in data1:
            return render_template('fragmento/familia/lista.html', lista=data1.get("data", []))
        else:
            return render_template('fragmento/familia/lista.html', lista=[], message='Respuesta inválida del servidor')
    except requests.RequestException as e:
        print(f"Error al realizar la solicitud: {e}")
        return render_template('fragmento/familia/lista.html', lista=[], message='Error al comunicarse con el backend')
    
    
# BUSCAR FAMILIAS- ROUTE
@router.route('/admin/family/search/<attribute>/<valor>/<method>', methods=["GET"])
def search_family(attribute, valor, method):
    try:
        r = requests.get(f"http://localhost:8099/myapp/family/searchFamily/{attribute}/{valor}/{method}")
        data = r.json()
        print(data)
        if r.status_code == 200:

            if method == "binary":
                lista = data.get("data", [])
                if isinstance(lista, dict):  
                    lista = [lista]
            else:
                lista = []
                if 'header' in data.get("data", {}):
                    lista.append(data.get("data", {}).get("header", {}).get("info", {}))
                    next_node = data.get("data", {}).get("header", {}).get("next", {})
                    while next_node:
                        lista.append(next_node.get("info", {}))
                        next_node = next_node.get("next", {})
            return render_template('fragmento/familia/lista.html', lista=lista)
        else:
            flash(f"Error al buscar: {data.get('data', 'Error desconocido')}", category='error')
            return redirect("/admin/family/list")
    except Exception as e:

        flash(f"Error inesperado: {str(e)}", category='error')
        return redirect("/admin/family/list")