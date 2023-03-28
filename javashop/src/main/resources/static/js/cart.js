
async function addCart(cartObj){
	const response = await axios.post(`/cartrest/`, cartObj)
	return response.data
	
}

async function cartCheck(cartObj){
	const response = await axios.post(`/cartrest/check/`, cartObj)
	return response.data
	
}

async function modifyCart(cartObj){
	const response = await axios.post(`/cartrest/modify/`, cartObj)
	return response.data
	
}