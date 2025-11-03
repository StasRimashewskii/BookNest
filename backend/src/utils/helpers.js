const sanitizeInput = (input) => {
  if (typeof input === 'string') {
    return input.trim().replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, '');
  }
  return input;
};

const formatResponse = (data, message = 'Success') => {
  return {
    success: true,
    message,
    data
  };
};

module.exports = {
  sanitizeInput,
  formatResponse
};